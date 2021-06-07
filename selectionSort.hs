selectionSort :: (Ord a) => [a] -> [a]
selectionSort array = foldl (\acc num -> (take num acc) ++ (shiftMinMemner $ (drop num acc))) array [0..length array - 2]

shiftMinMemner :: (Ord a) => [a] -> [a]
shiftMinMemner array = foldl (\acc x -> if head acc > x then x:(tail acc ++ [head acc]) else acc ++ [x]) [head array] (tail array)

toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> [Int]
parseInputs inputs = map toInt inputs

main = do
  args <- getLine
  let inputs = parseInputs  (words args)
  let maxBubble = max (head inputs) (head.tail $ inputs)
  let sortedNumbers =  selectionSort inputs
  putStrLn ("sorted:\n" ++ (show sortedNumbers :: String))
