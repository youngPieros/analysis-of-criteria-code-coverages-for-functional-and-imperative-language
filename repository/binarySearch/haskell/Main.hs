binarySearchWithRange :: (Ord a) => a -> [a] -> Int -> Int -> Int
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
parseInputs inputs = (toInt (head $ tail inputs), map toInt (tail $ tail inputs))

loop :: Int -> (IO()) -> IO()
loop 0 _ = return()
loop n f = do
  f
  loop (n - 1) f

searchNumber = do
  args <- getLine
  let inputs = parseInputs (words args)
  let searchNumber = fst inputs
  let collection = snd inputs
  let index = binarySearch searchNumber collection
  putStrLn (show index :: String)


main = do
  collectionNumbers <- getLine
  loop (toInt collectionNumbers) searchNumber
