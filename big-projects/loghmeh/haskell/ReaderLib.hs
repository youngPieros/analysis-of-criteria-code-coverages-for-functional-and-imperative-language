{-# LANGUAGE LambdaCase #-}
{-# LANGUAGE Rank2Types #-}
{-# LANGUAGE ScopedTypeVariables #-}

module ReaderLib
( Scanner(..)
, bstr
, line
, int
, times
, pair
, str
, many
) where


import Control.Applicative (liftA2)
import Control.Monad (replicateM)
import Control.Monad.State (State, evalState, get, gets, put)
import qualified Data.Set as Set
import qualified Data.ByteString.Lazy.Char8 as C
import Data.Maybe (fromJust)


type Scanner = State [C.ByteString]

bstr :: Scanner C.ByteString
bstr = get >>= \case s : ss -> put ss >> return s

line :: Scanner String
line = C.unpack . C.takeWhile (\c -> c /= '\n') <$> bstr

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
