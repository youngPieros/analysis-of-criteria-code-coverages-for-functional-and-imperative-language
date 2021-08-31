{-# LANGUAGE LambdaCase #-}
{-# LANGUAGE ScopedTypeVariables #-}

module ReaderLib
( Scanner(..)
, bstr
, line
, many
) where


import Control.Monad.State (State, get, put)
import qualified Data.ByteString.Lazy.Char8 as C


type Scanner = State [C.ByteString]

bstr :: Scanner C.ByteString
bstr = get >>= \case s : ss -> put ss >> return s

line :: Scanner String
line = C.unpack . C.takeWhile (\c -> c /= '\n') <$> bstr

many :: Scanner a -> Scanner [a]
many s = get >>= \case [] -> return []; _ -> (:) <$> s <*> many s
