quickSort :: (Ord a) => [a] -> [a]
quickSort [] = []
quickSort (x:xs) = leftSortedArray ++ [x] ++ rightSortedArray
	where
		leftSortedArray = quickSort $ filter (<x) xs
		rightSortedArray = quickSort $ filter (>=x) xs

toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> [Int]
parseInputs inputs = map toInt inputs

main = do
  args <- getLine
  let inputs = parseInputs  (words args)
  let sortedNumbers =  quickSort inputs
  putStrLn ("sorted:\n" ++ (show sortedNumbers :: String))
