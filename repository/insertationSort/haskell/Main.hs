insertionSort :: (Ord a) => [a] -> [a]
insertionSort [] = []
insertionSort (x:xs) = foldl (\acc x -> insert acc x) [x] (tail xs) 

insert :: (Ord a) => [a] -> a -> [a]
insert [] x = [x]
insert array x = if last array <= x then array ++ [x] else (insert (init array) x) ++ [last array]


toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> [Int]
parseInputs inputs = map toInt inputs

main = do
  numberCounts <- getLine
  args <- getLine
  let inputs = parseInputs  (words args)
  let sortedNumbers =  insertionSort inputs
  putStrLn ("sorted:\n" ++ (show sortedNumbers :: String))
