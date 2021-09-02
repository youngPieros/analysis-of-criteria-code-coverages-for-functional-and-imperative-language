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
    | otherwise = BadCommand


parseAddOfferingCommand :: String -> Command
parseAddOfferingCommand json = if Data.Maybe.isJust args then AddCourse (Data.Maybe.fromJust args) else BadCommand
    where
        args = (decode (C.pack json) :: Maybe AddCourseArgument)
