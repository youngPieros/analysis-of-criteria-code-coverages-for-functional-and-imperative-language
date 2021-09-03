module Handler
( run ) where

import qualified Data.ByteString.Lazy.Char8 as C
import Control.Monad.State (State, evalState)

import DataBase
import Command
import CommandArguments
import CommandParser
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
    commands <- parseCommands
    let database = getEmptyDataBase
    let systemState = foldl (\ss command -> changeSystemState ss (runCommand command (fst ss))) (database, []) commands
    let responses = (unlines $ filter (\r -> r /= "") $ map (\(Response r) -> r) (snd systemState))
    return responses


runCommand :: Command -> DataBase -> (DataBase, Response)
runCommand command database = case command of
    AddCourse args -> Application.addCourse database args
    AddStudent args -> Application.addStudent database args
    GetCourses args -> Application.getCourses database args
    GetCourse args -> Application.getCourse database args
    AddToWeeklySchedule args -> Application.addToWeeklySchedule database args
    RemoveFromWeeklySchedule args -> Application.removeFromWeeklySchedule database args
    BadCommand -> (database, Response "bad command!!!")


run :: IO ()
run = C.interact $ runScanner (C.pack <$> runScript)
