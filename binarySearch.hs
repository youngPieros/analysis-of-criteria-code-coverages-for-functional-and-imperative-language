binarySearchWithRange :: (Ord a) => a -> [a] -> Int -> Int -> Int
binarySearchWithRange _ [] _ _ = -1
binarySearchWithRange a members firstRange endRange 
 | firstRange >= endRange = -1
 | members !! middleIndex == a = middleIndex
 | (members !! middleIndex) > a = binarySearchWithRange a members firstRange middleIndex
 | otherwise = binarySearchWithRange a members (middleIndex + 1) endRange
 where middleIndex = div (firstRange + endRange) 2

binarySearch :: (Ord a) => a -> [a] -> Int
binarySearch a members = binarySearchWithRange a members 0 (length members)


toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> (Int, [Int])
parseInputs inputs = (toInt (head inputs), map toInt (tail inputs))

main = do
 args <- getLine
 let inputs = parseInputs  (words args)
 let index = binarySearch (fst inputs) (snd inputs)
 putStrLn ("index: " ++ (show index :: String))
