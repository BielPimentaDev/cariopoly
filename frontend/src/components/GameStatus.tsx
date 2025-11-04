interface GameStatusProps {
  currentPlayerName: string;
  currentPlayerColor: string;
  message: string;
}

const GameStatus = ({ currentPlayerName, currentPlayerColor, message }: GameStatusProps) => {
  return (
    <div className="w-full max-w-4xl mx-auto">
      <div className="bg-card rounded-xl p-6 shadow-card-custom border border-border">
        <div className="flex items-center gap-4">
          <div
            className="w-12 h-12 rounded-full border-4 border-white shadow-lg"
            style={{ backgroundColor: currentPlayerColor }}
          />
          <div className="flex-1">
            <h2 className="text-2xl font-bold text-foreground">
              Turno: <span style={{ color: currentPlayerColor }}>{currentPlayerName}</span>
            </h2>
            {message && (
              <p className="text-sm text-muted-foreground mt-1">{message}</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default GameStatus;
