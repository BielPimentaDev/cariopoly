interface Player {
  id: number;
  name: string;
  balance: number;
  color: string;
  position: number;
}

interface PlayerInfoProps {
  players: Player[];
  currentPlayer: number;
}

const PlayerInfo = ({ players, currentPlayer }: PlayerInfoProps) => {
  return (
    <div className="w-full max-w-4xl mx-auto grid grid-cols-2 md:grid-cols-4 gap-4">
      {players.map((player) => (
        <div
          key={player.id}
          className={`
            p-4 rounded-xl shadow-card-custom transition-all duration-300
            ${
              currentPlayer === player.id
                ? "bg-card border-2 scale-105 "
                : "bg-card/50 border border-border"
            }
          `}
          style={{
            borderColor: currentPlayer === player.id ? player.color : undefined,
          }}
        >
          <div className="flex items-center gap-3 mb-2">
            <div
              className="w-10 h-10 rounded-full border-2 border-white shadow-md"
              style={{ backgroundColor: player.color }}
            />
            <div className="flex-1">
              <h3 className="font-bold text-sm text-foreground">{player.name}</h3>
              {currentPlayer === player.id && (
                <span className="text-xs text-primary font-semibold">Jogando agora</span>
              )}
            </div>
          </div>
          <div className="mt-2">
            <p className="text-xs text-muted-foreground">Saldo</p>
            <p className="text-lg font-bold text-secondary">
              R$ {player.balance.toLocaleString()}
            </p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default PlayerInfo;
