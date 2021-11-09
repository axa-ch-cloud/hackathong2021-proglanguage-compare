package mandelbrot

import (
	"reflect"
	"testing"
)

func TestDraw(t *testing.T) {
	type args struct {
		width  int64
		height int64
	}
	tests := []struct {
		name    string
		args    args
		wantRes []bool
	}{
		{"", args{width: 10, height: 10}, expected()},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if gotRes := Draw(tt.args.width, tt.args.height); !reflect.DeepEqual(gotRes.data, tt.wantRes) {
				t.Errorf("Draw() = %v, want %v", gotRes.data, tt.wantRes)
			}
		})
	}
}

func expected() []bool {
	return []bool{false, false, false, false, false, true, false, false, false, false,
		false, false, false, false, false, true, false, false, false, false,
		false, false, false, false, false, true, false, false, false, false,
		false, false, false, false, true, true, true, false, false, false,
		false, false, false, false, false, true, false, false, false, false,
		false, false, true, true, true, true, true, true, true, false,
		false, true, true, true, true, true, true, true, true, true,
		false, false, true, true, true, true, true, true, true, false,
		false, false, false, false, false, false, false, false, false, false,
		false, false, false, false, false, false, false, false, false, false}

}
