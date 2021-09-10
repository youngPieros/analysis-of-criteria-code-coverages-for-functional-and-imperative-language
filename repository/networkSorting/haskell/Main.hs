import Control.Monad.Cont
import Control.Monad(when)
import Data.List(transpose, sort)
import Data.List.Split(chunksOf)


toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> [Int]
parseInputs inputs = map toInt inputs

filterBadPacket :: String -> String
filterBadPacket = filter (\packet -> packet /= '-')

isSubLayerValid :: String -> Bool
isSubLayerValid layer = all (\x -> length (filter (==x) filteredLayer) == 2) filteredLayer
  where filteredLayer = filterBadPacket layer

isValidNetwork :: [String] -> Bool
isValidNetwork network = all (\net -> isSubLayerValid net) (transpose network)

applyStage :: [Char] -> [Int] -> [Int]
applyStage layer numbers = map snd (sort $ map (\(a, b) -> (b, a)) (concat sortedLayer ++ unsortedNumbers))
 where
	sortedLayer = map (\[a, b] -> [(min (fst a) (fst b), min (snd a) (snd b)), (max (fst a) (fst b), max (snd a) (snd b))]) numberLayers
	numberLayers = chunksOf 2 (zip (map (\(_, b, _) -> b) matchedPackets) (map (\(_, _, c) -> c) matchedPackets))
	unsortedNumbers = zip (map (\(_, b, _) -> b) unmatchedPackets) (map (\(_, _, c) -> c) unmatchedPackets)
	unmatchedPackets = filter (\(char, _, _) -> char == '-') sortedZipLayer
	matchedPackets = filter (\(char, _, _) -> char /= '-') sortedZipLayer
	sortedZipLayer = sort (zip3 layer numbers [0..length layer])

apply :: [String] -> [Int] -> [Int]
apply network numbers = foldl (\reorderedNumbers sublayer -> applyStage sublayer reorderedNumbers) numbers (transpose network)

process :: [String] -> [Int] -> String
process network numbers
    | not $ isValidNetwork network = "Invalid network"
    | isSorted = "Sorted"
    | otherwise = "Not Sorted"
    where
        isSorted = all (\(a, b) -> a <= b) (zip (init reorderNumbers) (tail reorderNumbers))
        reorderNumbers = apply network numbers

getInputs = do
  args <- getLine
  return (parseInputs (words args))

getNetwork = do
  args <- getLine
  let network = words args
  numbers <- getInputs
  return (network, numbers)


handleNetwork = do
  parameters <- getInputs
  let networkSize = parameters !! 0
  let numberOfStage = parameters !! 1
  when (networkSize /= 0 && numberOfStage /= 0) $ do
    arguments <- getNetwork
    let network = fst arguments
    let numbers = snd arguments
    let result = process network numbers
    putStrLn result
    handleNetwork


main = do
  handleNetwork
