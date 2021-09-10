{-# LANGUAGE LambdaCase #-}
{-# LANGUAGE Rank2Types #-}
{-# LANGUAGE ScopedTypeVariables #-}

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

data Query = AddRelation Int Int | RemoveRelation Int Int | Kill
type TestCase = (Int, [(Int, Int)], [Query])

query :: Scanner Query
query = do
  queryType <- int
  case queryType of
    1 -> AddRelation <$> int <*> int
    2 -> RemoveRelation <$> int <*> int
    3 -> pure Kill

showInLine :: (Show a) => [a] ->  String
showInLine array = unlines (map show array)

runScript :: Scanner String
runScript = do
  testCase <- testCase
  let result = solve testCase
  return (showInLine result)

testCase :: Scanner TestCase
testCase = do
  numberOfHumans <- int
  numberOfRelations <- int
  edges <- times numberOfRelations (pair int int)
  queryNumbers <- int
  queries <- times queryNumbers query
  return (numberOfHumans, edges, queries)

solve :: TestCase -> [Int]
solve (n, edges, queries) = map (findKillResult n) toBeProcessedRelations
  where
    toBeProcessedRelations = map (\x -> fst x) (filter (\x -> needToProcess (snd x)) $ zip allRelations queries)
    allRelations = findRelationsShouldBeProcess edges queries


findKillResult :: Int -> [(Int, Int)] -> Int
findKillResult n relations = n - (Set.size killedNoblesSet)
  where
    killedNoblesSet = Set.fromList killedNobles
    killedNobles = map (\(a, _) -> a) (filter (\(a, b) -> a < b) allUnDirectionalEdges)
    allUnDirectionalEdges = relations ++ map (\(a, b) -> (b, a)) relations

findRelationsShouldBeProcess :: [(Int, Int)] -> [Query] -> [[(Int, Int)]]
findRelationsShouldBeProcess edges queries = tail $ foldl (\acc query -> acc ++ [newRelation (last acc) query]) [edges] queries

newRelation :: [(Int, Int)] -> Query -> [(Int, Int)]
newRelation relations query = case query of
  AddRelation u v -> add relations (u, v)
  RemoveRelation u v -> remove relations (u, v)
  Kill -> relations

needToProcess :: Query -> Bool
needToProcess query = case query of
  Kill -> True
  otherwise -> False

add :: [(Int, Int)] -> (Int, Int) -> [(Int, Int)]
add relations relation = relation:relations

remove :: [(Int, Int)] -> (Int, Int) -> [(Int, Int)]
remove relations r = filter (\x -> not ((fst x == fst r && snd x == snd r) || (fst x == snd r && snd x == fst r))) relations
