quickSort :: (Ord a) => [a] -> [a]
quickSort [] = []
quickSort array = leftSortedArray ++ [pivot] ++ rightSortedArray
	where
		partitions = partition array
		leftSortedArray = quickSort (take (snd partitions) (fst partitions))
		rightSortedArray = quickSort (drop (snd partitions + 1) (fst partitions))
		pivot = (fst partitions) !! (snd partitions)

partition :: (Ord a) => [a] -> ([a], Int)
partition array = (lessThatPivots ++ [pivot] ++ greatherThanPivots, length lessThatPivots)
	where
		pivot = last array
		lessThatPivots = filter (<pivot) (init array)
		greatherThanPivots = filter (>=pivot) (init array)

betterImplementedQuickSort :: (Ord a) => [a] -> [a]
betterImplementedQuickSort [] = []
betterImplementedQuickSort (x:xs) = leftSortedArray ++ [x] ++ rightSortedArray
	where
		leftSortedArray = betterImplementedQuickSort $ filter (<x) xs
		rightSortedArray = betterImplementedQuickSort $ filter (>=x) xs



toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> [Int]
parseInputs inputs = map toInt inputs

main = do
  args <- getLine
  let inputs = parseInputs  (words args)
  let sortedNumbers =  betterImplementedQuickSort inputs
  putStrLn ("sorted:\n" ++ (show sortedNumbers :: String))
