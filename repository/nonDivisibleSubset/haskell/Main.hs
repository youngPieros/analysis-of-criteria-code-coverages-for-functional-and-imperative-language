{-# LANGUAGE LambdaCase #-}

import Control.Monad (replicateM)
import Control.Monad.State (State, evalState, get, put)
import qualified Data.ByteString.Lazy.Char8 as C
import Data.Maybe (fromJust)
import Data.List
import Data.Function

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

main :: IO ()
main = C.interact $ runScanner (C.pack <$> runScript)

runScript :: Scanner String
runScript = do
  n <- int
  k <- int
  numbers <- times n int
  let result = (show (solve k numbers) :: String) ++ "\n"
  return result

solve :: Int -> [Int] -> Int
solve k numbers = (sum max_non_divisible_numbers_per_group) + (length single_buckets)
  where
    max_non_divisible_numbers_per_group = map (\x -> foldl (\acc y -> max acc (snd y)) 0 x) grouped_tuple_buckets
    grouped_tuple_buckets = groupBy (\a b -> (fst a) + (fst b) == k) tuple_buckets
    tuple_buckets = filter (\b -> rem (2 * (fst b)) k /= 0) grouped_buckets
    single_buckets = filter (\b -> rem (2 * (fst b)) k == 0) grouped_buckets
    grouped_buckets = sortBy (compare `on` (\b -> min (k - (fst b)) (fst b))) buckets
    buckets = map (\g -> (g !! 0, length g)) (group rem_numbers) 
    rem_numbers = sort $ map (flip rem k) numbers
