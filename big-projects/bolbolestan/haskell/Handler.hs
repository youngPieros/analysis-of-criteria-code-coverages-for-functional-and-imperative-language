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
import DTO


type Scanner = State [C.ByteString]
type SystemState = (DataBase, [Response])

runScanner :: Scanner a -> C.ByteString -> a
runScanner = runScannerWith C.lines

runScannerWith :: (C.ByteString -> [C.ByteString]) -> Scanner a -> C.ByteString -> a
runScannerWith t s = evalState s . t

changeSystemState :: SystemState -> (DataBase, Response) -> SystemState
changeSystemState systemState (database, response) = (database, snd systemState ++ [response])

runProgram :: Scanner String
runProgram = do
    commands <- parseCommands
    let database = getEmptyDataBase
    let systemState = foldl (\ss command -> changeSystemState ss (exec command (fst ss))) (database, []) commands
    let responses = unlines $ map (toDTO) (snd systemState)
    return responses


exec :: Command -> DataBase -> (DataBase, Response)
exec command database = case command of
    AddCourse args -> Application.addCourse database args
    AddStudent args -> Application.addStudent database args
    GetCourses args -> Application.getCourses database args
    GetCourse args -> Application.getCourse database args
    AddToWeeklySchedule args -> Application.addToWeeklySchedule database args
    RemoveFromWeeklySchedule args -> Application.removeFromWeeklySchedule database args
    GetWeeklySchedule args -> Application.getStudentWeeklySchedule database args
    FinalizeSchedule args -> Application.finalizeWeeklySchedule database args
    Command.BadCommand -> (database, Response.BadCommand)


run :: IO ()
run = C.interact $ runScanner (C.pack <$> runProgram)
