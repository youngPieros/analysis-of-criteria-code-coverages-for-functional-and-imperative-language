bubbleSort :: (Ord a) => [a] -> [a]
bubbleSort [] = []
bubbleSort array = foldl (\acc num -> ((shiftMaxBubble (take num acc)) ++ (drop num acc))) array (reverse [1..length array])

shiftMaxBubble :: (Ord a) => [a] -> [a]
shiftMaxBubble [] = []
shiftMaxBubble [a] = [a]
shiftMaxBubble array = minBubble:shiftMaxBubble (maxBubble:(drop 2 array))
 where 
	minBubble = min (head array) (head.tail $ array)
	maxBubble = max (head array) (head.tail $ array)

toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> [Int]
parseInputs inputs = map toInt inputs

main = do
  args <- getLine
  let inputs = parseInputs  (words args)
  let maxBubble = max (head inputs) (head.tail $ inputs)
  let sortedNumbers =  bubbleSort inputs
  putStrLn ("sorted:\n" ++ (show sortedNumbers :: String))
