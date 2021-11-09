package db

type Entity struct {
	Result [][]bool `json:"result"`
}

func New(result [][]bool) Entity {
	return Entity{
		Result: result,
	}
}
