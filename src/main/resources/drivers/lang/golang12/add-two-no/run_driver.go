package main

import (
	"fmt"
	"strconv"
)

func main() {
	noTestCases, _ := strconv.Atoi(firstNumStr)
	for i := 0; i < noTestCases; i++ {
		n1 := parseInt(getInput())
		n2 := parseInt(getInput())
		actual := add(n1, n2)
		expected := sol_add(n1, n2)
		if actual != expected {
			println("Pass:"+ actual + "~"+ expected)
		} else {
			println("Fail:"+ actual + "~"+ expected)
		}
	}
}

// Helper function to read input from console
func getInput() string {
	var input string
	fmt.Scanln(&input)
	return input
}

func parseInt(val string) int {
	intVal, _ := strconv.Atoi(val)
	if _ != nil {
		panic("input should be integer")
	}
	return intVal
}

func sol_add(n1, n2 int) int {
	return n1 + n2
}

// add code below
