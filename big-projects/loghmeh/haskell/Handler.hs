module Handler
( run ) where


import qualified Data.ByteString.Lazy.Char8 as C
import Control.Monad.State (State, evalState)

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
    let responses = (unlines $ filter (\r -> r /= "") $ map (\(Response r) -> r) (snd systemState))
    return responses


runCommand :: Command -> DataBase -> (DataBase, Response)
runCommand command database = case command of
    AddRestaurant args -> Application.addRestaurant (AddRestaurant args) database
    AddFood args -> Application.addFood (AddFood args) database
    GetRestaurants -> getRestaurants GetRestaurants database
    GetRestaurant args -> Application.getRestaurant (GetRestaurant args) database
    GetFood args -> Application.getFood (GetFood args) database
    AddToCart args -> addToCart (AddToCart args) database
    GetCart -> getCart GetCart database
    FinalizeOrder -> finalizeOrder FinalizeOrder database
    GetRecommendedRestaurants -> getRecommendedRestaurants GetRecommendedRestaurants database
    BadCommand -> (database, Response "bad command!!!")


run :: IO ()
run = C.interact $ runScanner (C.pack <$> runScript)
