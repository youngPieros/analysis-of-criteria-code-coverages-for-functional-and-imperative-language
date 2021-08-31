module Handler
( run ) where

import qualified Data.ByteString.Lazy.Char8 as C
import Control.Monad.State (State, evalState)

import Restaurant
import Food
import Order
import Location
import DataBase
import Command
import CommandArgument
import Response
import Application

type Scanner = State [C.ByteString]
type SystemState = (DataBase, [Response])

changeSystemState :: SystemState -> (DataBase, Response) -> SystemState
changeSystemState systemState (database, response) = (database, snd systemState ++ [response])

runScanner :: Scanner a -> C.ByteString -> a
runScanner = runScannerWith C.lines

runScannerWith :: (C.ByteString -> [C.ByteString]) -> Scanner a -> C.ByteString -> a
runScannerWith t s = evalState s . t


runScript :: Scanner String
runScript = do
    commands <- getCommands
    let database = getEmptyDataBase
    let systemState = foldl (\ss command -> changeSystemState ss (runCommand command (fst ss))) (database, []) commands
    let responses = (unlines $ map (\(Response r) -> r) (snd systemState))
    let databaseState = (show (fst systemState) :: String)
    return (responses ++ "\n\n\n\n" ++ databaseState)


runCommand :: Command -> DataBase -> (DataBase, Response)
runCommand command database = case command of
    AddRestaurant args -> addRestaurant (AddRestaurant args) database
    AddFood args -> addFood (AddFood args) database
    GetRestaurants -> getRestaurants GetRestaurants database
    GetRestaurant args -> Application.getRestaurant (GetRestaurant args) database
    GetFood args -> Application.getFood (GetFood args) database
    AddToCart args -> addToCart (AddToCart args) database
    GetCart -> getCart GetCart database
    FinalizeOrder -> finalizeOrder FinalizeOrder database
    BadCommand -> (database, Response "bad command!!!")


run :: IO ()
run = C.interact $ runScanner (C.pack <$> runScript)
