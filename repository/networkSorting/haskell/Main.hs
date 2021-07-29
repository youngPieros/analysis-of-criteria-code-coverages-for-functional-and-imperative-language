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

reorderNumbersBasedOnSubnet :: [Char] -> [Int] -> [Int]
reorderNumbersBasedOnSubnet layer numbers = map snd (sort $ map (\(a, b) -> (b, a)) (concat sortedLayer ++ unsortedNumbers))
 where
	sortedLayer = map (\[a, b] -> [(min (fst a) (fst b), min (snd a) (snd b)), (max (fst a) (fst b), max (snd a) (snd b))]) numberLayers
	numberLayers = chunksOf 2 (zip (map (\(_, b, _) -> b) matchedPackets) (map (\(_, _, c) -> c) matchedPackets))
	unsortedNumbers = zip (map (\(_, b, _) -> b) unmatchedPackets) (map (\(_, _, c) -> c) unmatchedPackets)
	unmatchedPackets = filter (\(char, _, _) -> char == '-') sortedZipLayer
	matchedPackets = filter (\(char, _, _) -> char /= '-') sortedZipLayer
	sortedZipLayer = sort (zip3 layer numbers [0..length layer])

reorderNumbersBasedOnNetwork :: [String] -> [Int] -> [Int]
reorderNumbersBasedOnNetwork network numbers = foldl (\reorderedNumbers sublayer -> reorderNumbersBasedOnSubnet sublayer reorderedNumbers) numbers (transpose network)

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
    let isValid = isValidNetwork network
    when (not isValid) $ do
      putStrLn ("Invalid network")
    when (isValid) $ do
      let reorderNumbers = reorderNumbersBasedOnNetwork network numbers
      let isSorted = all (\(a, b) -> a <= b) (zip (init reorderNumbers) (tail reorderNumbers))
      when (isSorted) $ do
        putStrLn("Sorted")
      when (not isSorted) $ do
        putStrLn("Not Sorted")
    handleNetwork


main = do
  handleNetwork
