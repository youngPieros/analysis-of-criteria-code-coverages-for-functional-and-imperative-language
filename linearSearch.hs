linearSearch :: (Eq a) => a -> [a] -> Int
linearSearch _ [] = -1
linearSearch a (x:xs) = if x == a then 0 else (if (indexOfTailSearch == -1) then (-1) else (1 + indexOfTailSearch))
	where indexOfTailSearch = linearSearch a xs


toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> (Int, [Int])
parseInputs inputs = (toInt (head inputs), map toInt (tail inputs))

main = do
	args <- getLine
	let inputs = parseInputs  (words args)
	let index = linearSearch (fst inputs) (snd inputs)
	putStrLn ("index: " ++ (show index :: String))
