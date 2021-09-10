{-# LANGUAGE LambdaCase #-}

import Control.Applicative (liftA2)
import Control.Monad (replicateM)
import Control.Monad.State (State, evalState, get, put)
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

main :: IO ()
main = C.interact $ runScanner (C.pack <$> runScript)

type Task = (Int, Int)

getTask :: Scanner Task
getTask = do
  task <- pair int int
  return task

binaryInsert :: (a -> a -> Bool) -> a -> [a] -> [a]
binaryInsert _ x [] = [x]
binaryInsert orderF x array = if (orderF x root) then (binaryInsert orderF x left) ++ [root] ++ right else (left ++ [root] ++ (binaryInsert orderF x right))
  where
    left = take middleIndex array
    right = drop (middleIndex + 1) array
    root = array !! middleIndex
    middleIndex = div (length array) 2


isSmallerThan :: Task -> Task -> Bool
isSmallerThan (d1, m1) (d2, m2) = if (d1 < d2 || d1 == d2 && m1 < m2) then True else False

solve :: Task -> [Task] -> ([Task], Int)
solve task tasks = (newOrderOfTasks, result)
  where
    result = foldl (\acc (a, b) -> max acc (a - b)) 0 (zip deadlineAggregations deadlines)
    deadlineAggregations = tail $ scanl (+) 0 minutes
    deadlines = map (\task -> fst task) newOrderOfTasks
    minutes = map (\task -> snd task) newOrderOfTasks
    newOrderOfTasks = binaryInsert isSmallerThan task tasks

mergeStates :: ([Task], [Int]) -> ([Task], Int) -> ([Task], [Int])
mergeStates (_, results) (tasks, result) = (tasks, results ++ [result])

runScript :: Scanner String
runScript = do
  numberOfTasks <- int
  tasks <- times numberOfTasks getTask
  let results = foldl (\acc x -> mergeStates acc (solve x (fst acc))) ([], []) tasks
  let output = snd results
  return (unlines $ map (\x -> show x :: String) output)
