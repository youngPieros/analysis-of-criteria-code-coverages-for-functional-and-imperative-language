{-# LANGUAGE LambdaCase #-}
{-# LANGUAGE Rank2Types #-}
{-# LANGUAGE ScopedTypeVariables #-}

module Command
( Command(..)
, getCommands
) where



import Control.Applicative (liftA2)
import Control.Monad (replicateM)
import Control.Monad.State (State, evalState, get, gets, put)
import qualified Data.Set as Set
import qualified Data.ByteString.Lazy.Char8 as C
import Data.Maybe (fromJust)

type Scanner = State [C.ByteString]

runScanner :: Scanner a -> C.ByteString -> a
runScanner = runScannerWith C.words

runScannerWith :: (C.ByteString -> [C.ByteString]) -> Scanner a -> C.ByteString -> a
runScannerWith t s = evalState s . t

bstr :: Scanner C.ByteString
bstr = get >>= \case s : ss -> put ss >> return s

int :: Scanner Int
int = fst . fromJust . C.readInt <$> bstr

times :: Int -> Scanner a -> Scanner [a]
times = replicateM

pair :: Scanner a -> Scanner b -> Scanner (a, b)
pair = liftA2 (,)

str :: Scanner String
str = C.unpack <$> bstr

many :: Scanner a -> Scanner [a]
many s = get >>= \case [] -> return []; _ -> (:) <$> s <*> many s

data Command = AddRelation Int Int | RemoveRelation Int Int | Kill deriving (Eq, Show)

command :: Scanner Command
command = do
  commandType <- int
  case commandType of
    1 -> AddRelation <$> int <*> int
    2 -> RemoveRelation <$> int <*> int
    3 -> pure Kill

getCommands :: Scanner [Command]
getCommands = do
  commands <- many command
  return commands
