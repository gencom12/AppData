# Tic Tac Toe implementation
def print_board(board):
    for row in board:
        print(" | ".join(row))
        print("-" * 9)

def check_winner(board):
    # Check rows, columns, and diagonals
    for i in range(3):
        if board[i][0] == board[i][1] == board[i][2] != ' ' or \
           board[0][i] == board[1][i] == board[2][i] != ' ':
            return True
    if board[0][0] == board[1][1] == board[2][2] != ' ' or \
       board[0][2] == board[1][1] == board[2][0] != ' ':
        return True
    return False

def is_board_full(board):
    return all(all(cell != ' ' for cell in row) for row in board)

def get_available_moves(board):
    return [(i, j) for i in range(3) for j in range(3) if board[i][j] == ' ']

# Minimax algorithm with alpha-beta pruning
def minimax(board, depth, maximizing_player, alpha, beta):
    if check_winner(board):
        return -1 if maximizing_player else 1  # Opposite value for winner

    if is_board_full(board):
        return 0  # Draw

    available_moves = get_available_moves(board)

    if maximizing_player:
        max_eval = float('-inf')
        for move in available_moves:
            board[move[0]][move[1]] = 'X'
            eval = minimax(board, depth + 1, False, alpha, beta)
            board[move[0]][move[1]] = ' '  # Undo move
            max_eval = max(max_eval, eval)
            alpha = max(alpha, eval)
            if beta <= alpha:
                break
        return max_eval
    else:
        min_eval = float('inf')
        for move in available_moves:
            board[move[0]][move[1]] = 'O'
            eval = minimax(board, depth + 1, True, alpha, beta)
            board[move[0]][move[1]] = ' '  # Undo move
            min_eval = min(min_eval, eval)
            beta = min(beta, eval)
            if beta <= alpha:
                break
        return min_eval

def get_best_move(board):
    best_val = float('-inf')
    best_move = None
    alpha = float('-inf')
    beta = float('inf')

    for move in get_available_moves(board):
        board[move[0]][move[1]] = 'X'
        move_val = minimax(board, 0, False, alpha, beta)
        board[move[0]][move[1]] = ' '  # Undo move

        if move_val > best_val:
            best_val = move_val
            best_move = move

    return best_move

# Play the game
def play_tic_tac_toe():
    board = [[' ' for _ in range(3)] for _ in range(3)]

    while not check_winner(board) and not is_board_full(board):
        print_board(board)
        player_move = tuple(map(int, input("Enter your move (row and column): ").split()))
        if board[player_move[0]][player_move[1]] == ' ':
            board[player_move[0]][player_move[1]] = 'O'
        else:
            print("Invalid move. Try again.")
            continue

        if check_winner(board):
            print_board(board)
            print("You win!")
            break
        elif is_board_full(board):
            print_board(board)
            print("It's a draw!")
            break

        print("Computer's move:")
        computer_move = get_best_move(board)
        board[computer_move[0]][computer_move[1]] = 'X'

        if check_winner(board):
            print_board(board)
            print("Computer wins!")
            break
        elif is_board_full(board):
            print_board(board)
            print("It's a draw!")
            break

if __name__ == "__main__":
    play_tic_tac_toe()
