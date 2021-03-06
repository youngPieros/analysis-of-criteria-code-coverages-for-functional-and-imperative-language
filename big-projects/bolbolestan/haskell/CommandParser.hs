module CommandParser
( parseCommands
) where

import qualified Data.ByteString.Lazy.Char8 as C
import Data.Maybe
import Data.Aeson
import Data.List

import Command
import CommandArguments
import ReaderLib



parseCommands :: Scanner [Command]
parseCommands = do
  commands <- many command
  return commands


command :: Scanner Command
command = do
  commandStr <- line
  let commandName = head . words $ commandStr
  let args = unwords . tail . words $ commandStr
  return (parseCommand commandName args)


parseCommand :: String -> String -> Command
parseCommand commandName args
    | commandName == "addOffering" = parseAddOfferingCommand args
    | commandName == "addStudent" = parseAddStudentCommand args
    | commandName == "getOfferings" = parseGetCoursesArgument args
    | commandName == "getOffering" = parseGetCourseArgument args
    | commandName == "addToWeeklySchedule" = parseAddToWeeklyScheduleArgument args
    | commandName == "removeFromWeeklySchedule" = parseRemoveFromWeeklyScheduleArgument args
    | commandName == "getWeeklySchedule" = parseGetWeeklyScheduleArgument args
    | commandName == "finalize" = parseFinalizeScheduleArgument args
    | otherwise = BadCommand


parseAddOfferingCommand :: String -> Command
parseAddOfferingCommand json = if Data.Maybe.isJust args then AddCourse (Data.Maybe.fromJust args) else BadCommand
    where
        args = (decode (C.pack json) :: Maybe AddCourseArgument)

parseAddStudentCommand :: String -> Command
parseAddStudentCommand json = if Data.Maybe.isJust args then AddStudent (Data.Maybe.fromJust args) else BadCommand
    where
        args = (decode (C.pack json) :: Maybe AddStudentArgument)

parseGetCoursesArgument :: String -> Command
parseGetCoursesArgument json = if Data.Maybe.isJust args then GetCourses (Data.Maybe.fromJust args) else BadCommand
    where
        args = decode (C.pack json) :: Maybe GetCoursesArgument

parseGetCourseArgument :: String -> Command
parseGetCourseArgument json = if Data.Maybe.isJust args then GetCourse (Data.Maybe.fromJust args) else BadCommand
    where
        args = decode (C.pack json) :: Maybe GetCourseArgument

parseAddToWeeklyScheduleArgument :: String -> Command
parseAddToWeeklyScheduleArgument json = if Data.Maybe.isJust args then AddToWeeklySchedule (Data.Maybe.fromJust args) else BadCommand
    where
        args = decode (C.pack json) :: Maybe AddToWeeklyScheduleArgument

parseRemoveFromWeeklyScheduleArgument :: String -> Command
parseRemoveFromWeeklyScheduleArgument json = if Data.Maybe.isJust args then RemoveFromWeeklySchedule (Data.Maybe.fromJust args) else BadCommand
    where
        args = decode (C.pack json) :: Maybe RemoveFromWeeklyScheduleArgument

parseGetWeeklyScheduleArgument :: String -> Command
parseGetWeeklyScheduleArgument json = if Data.Maybe.isJust args then GetWeeklySchedule (Data.Maybe.fromJust args) else BadCommand
    where
        args = decode (C.pack json) :: Maybe GetWeeklyScheduleArgument

parseFinalizeScheduleArgument :: String -> Command
parseFinalizeScheduleArgument json = if Data.Maybe.isJust args then FinalizeSchedule (Data.Maybe.fromJust args) else BadCommand
    where
        args = decode (C.pack json) :: Maybe FinalizeScheduleArgument

