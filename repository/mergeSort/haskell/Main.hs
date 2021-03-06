mergeSort :: (Ord a) => [a] -> [a]
mergeSort [] = []
mergeSort [a] = [a]
mergeSort array = merge leftSortedList rightSortedList
	where
		leftSortedList = mergeSort $ take middleIndex array
		rightSortedList = mergeSort $ drop middleIndex array
		middleIndex = div (length array) 2

merge :: (Ord a) => [a] -> [a] -> [a]
merge [] array = array
merge array [] = array
merge (x:xs) (y:ys) = if x < y then x: merge xs (y:ys) else y: merge (x:xs) ys


toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> [Int]
parseInputs inputs = map toInt inputs

main = do
  numberCounts <- getLine
  args <- getLine
  let inputs = parseInputs  (words args)
  let sortedNumbers =  mergeSort inputs
  putStrLn ("sorted:\n" ++ (show sortedNumbers :: String))
