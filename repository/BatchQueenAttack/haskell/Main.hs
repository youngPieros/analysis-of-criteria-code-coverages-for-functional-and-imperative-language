{-# LANGUAGE LambdaCase #-}

import Control.Applicative (liftA2)
import Control.Monad (replicateM)
import Control.Monad.State (State, evalState, get, put)
import qualified Data.ByteString.Lazy.Char8 as C
import Data.List
import Data.Function
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

type Point = (Int, Int)
data Direction = UP | DOWN | RIGHT | LEFT | RIGHT_UP | RIGHT_DOWN | LEFT_UP | LEFT_DOWN | NULL deriving(Eq)

get_direction_to :: Point -> Point -> Direction
get_direction_to (r1, c1) (r2, c2)
  | r1 == r2 = if c1 > c2 then LEFT else RIGHT
  | c1 == c2 = if r1 > r2 then DOWN else UP
  | abs (r1 - r2) /= abs (c1 - c2) = NULL
  | r1 > r2 && c1 > c2 = LEFT_DOWN
  | r1 > r2 = RIGHT_DOWN
  | c1 > c2 = LEFT_UP
  | otherwise = RIGHT_UP

get_distance :: Point -> Point -> Int
get_distance (r1, c1) (r2, c2)
  | r1 == r2 = abs (c1 - c2)
  | otherwise = abs (r1 - r2)

get_direction_number :: Direction -> Int
get_direction_number UP = 1
get_direction_number DOWN = 2
get_direction_number RIGHT = 3
get_direction_number LEFT = 4
get_direction_number RIGHT_UP = 5
get_direction_number RIGHT_DOWN = 6
get_direction_number LEFT_UP = 7
get_direction_number LEFT_DOWN = 8

runScript :: Scanner String
runScript = do
  n <- int
  k <- int
  queen_position <- pair int int
  obstacles <- times k (pair int int)
  let result = solve n queen_position obstacles
  return ((show result :: String) ++ "\n")

zipMap :: a -> [b] -> [(a, b)]
zipMap x arr = zip (take (length arr) (repeat x)) arr

zipMapR :: a -> [b] -> [(b, a)]
zipMapR x arr = map (\(x, y) -> (y, x)) (zipMap x arr)

solve :: Int -> Point -> [Point] -> Int
solve n queen_position obstacles = sum nearestDistanceObstacles
  where
    nearestDistanceObstacles = map (\obstaclesGroup -> (foldl (\acc (d, obs) -> min acc ((get_distance queen_position obs) - 1)) n obstaclesGroup)) groupedObstacles
    groupedObstacles = groupBy ((==) `on` fst) sortedObstacles
    sortedObstacles = sort $ map (\(direction, obstacle) -> (get_direction_number direction, obstacle)) filteredObstacles
    filteredObstacles = filter (\(direction, obstacle) -> direction /= NULL) obstaclesWithDirections
    obstaclesWithDirections = map (\obstacle -> (get_direction_to queen_position obstacle, obstacle)) wrapped_obstacles
    wrapped_obstacles = margin_obstacles ++ obstacles
    margin_obstacles = (zipMap 0 [0..n+1]) ++  (zipMap (n+1) [0..n+1]) ++ (zipMapR 0 [1..n]) ++ (zipMapR (n+1) [1..n])
