import { useMemo } from "react";

export type CellType = "property" | "start" | "prison" | "luck";

interface BoardCell {
  id: number;
  name: string;
  value: number;
  type: CellType;
  position: { x: number; y: number };
}

interface BoardProps {
  players: { id: number; position: number; color: string }[];
  isMoving: boolean;
  diceButton: React.ReactNode;
}

const Board = ({ players, isMoving, diceButton }: BoardProps) => {
  const cells: BoardCell[] = useMemo(() => {
    const locations: Array<{ name: string; value: number; type: CellType }> = [
      { name: "Início", value: 0, type: "start" },
      { name: "Copacabana", value: 350, type: "property" },
      { name: "Ipanema", value: 400, type: "property" },
      { name: "Sorte", value: 0, type: "luck" },
      { name: "Leblon", value: 450, type: "property" },
      { name: "Botafogo", value: 250, type: "property" },
      { name: "Flamengo", value: 280, type: "property" },
      { name: "Prisão", value: 0, type: "prison" },
      { name: "Barra da Tijuca", value: 500, type: "property" },
      { name: "Recreio", value: 320, type: "property" },
      { name: "Jardim Botânico", value: 380, type: "property" },
      { name: "Sorte", value: 0, type: "luck" },
      { name: "Lagoa", value: 420, type: "property" },
      { name: "Gávea", value: 360, type: "property" },
      { name: "São Conrado", value: 340, type: "property" },
      { name: "Sorte", value: 0, type: "luck" },
      { name: "Urca", value: 300, type: "property" },
      { name: "Leme", value: 260, type: "property" },
      { name: "Tijuca", value: 240, type: "property" },
      { name: "Grajaú", value: 200, type: "property" },
      { name: "Vila Isabel", value: 220, type: "property" },
      { name: "Sorte", value: 0, type: "luck" },
      { name: "Méier", value: 180, type: "property" },
      { name: "Madureira", value: 160, type: "property" },
      { name: "Centro", value: 300, type: "property" },
      { name: "Lapa", value: 280, type: "property" },
      { name: "Santa Teresa", value: 290, type: "property" },
      { name: "Glória", value: 270, type: "property" },
      { name: "Catete", value: 250, type: "property" },
      { name: "Laranjeiras", value: 310, type: "property" },
    ];

    const boardSize = 8;
    const positions: BoardCell[] = [];
    let index = 0;

    // Top row (left to right)
    for (let x = 0; x < boardSize; x++) {
      positions.push({
        id: index,
        name: locations[index].name,
        value: locations[index].value,
        type: locations[index].type,
        position: { x, y: 0 },
      });
      index++;
    }

    // Right column (top to bottom, excluding corners)
    for (let y = 1; y < boardSize - 1; y++) {
      positions.push({
        id: index,
        name: locations[index].name,
        value: locations[index].value,
        type: locations[index].type,
        position: { x: boardSize - 1, y },
      });
      index++;
    }

    // Bottom row (right to left)
    for (let x = boardSize - 1; x >= 0; x--) {
      positions.push({
        id: index,
        name: locations[index].name,
        value: locations[index].value,
        type: locations[index].type,
        position: { x, y: boardSize - 1 },
      });
      index++;
    }

    // Left column (bottom to top, excluding corners)
    for (let y = boardSize - 2; y > 0; y--) {
      positions.push({
        id: index,
        name: locations[index].name,
        value: locations[index].value,
        type: locations[index].type,
        position: { x: 0, y },
      });
      index++;
    }

    return positions;
  }, []);

  const getPlayersAtPosition = (position: number) => {
    return players.filter((p) => p.position === position);
  };

  const getCellColor = (type: CellType) => {
    switch (type) {
      case "start":
        return "bg-gradient-ocean border-primary";
      case "prison":
        return "bg-destructive/10 border-destructive";
      case "luck":
        return "bg-accent/20 border-accent";
      case "property":
      default:
        return "bg-board-cell border-board-border";
    }
  };

  return (
    <div className="relative w-full max-w-4xl mx-auto">
      <div
        className="grid gap-1 p-4 bg-board-bg rounded-xl shadow-board"
        style={{
          gridTemplateColumns: "repeat(8, 1fr)",
          gridTemplateRows: "repeat(8, 1fr)",
        }}
      >
        {cells.map((cell) => {
          const playersHere = getPlayersAtPosition(cell.id);
          const isInnerCell =
            cell.position.x > 0 &&
            cell.position.x < 7 &&
            cell.position.y > 0 &&
            cell.position.y < 7;

          return (
            <div
              key={cell.id}
              style={{
                gridColumn: cell.position.x + 1,
                gridRow: cell.position.y + 1,
              }}
              className={`
                relative h-20 rounded-md border-2 transition-all duration-300
                ${
                  isInnerCell
                    ? "bg-transparent border-transparent"
                    : `${getCellColor(cell.type)} hover:shadow-md`
                }
              `}
            >
              {!isInnerCell && (
                <>
                  <div className="absolute inset-0 p-2 flex flex-col items-center justify-center text-center">
                    <span className="text-xs font-bold text-foreground leading-tight line-clamp-2">
                      {cell.name}
                    </span>
                    {cell.value > 0 && (
                      <span className="text-[10px] font-semibold text-primary mt-1">
                        R$ {cell.value}
                      </span>
                    )}
                  </div>
                  {playersHere.length > 0 && (
                    <div className="absolute -top-2 -right-2 flex gap-1 z-10">
                      {playersHere.map((player) => (
                        <div
                          key={player.id}
                          className={`w-6 h-6 rounded-full border-2 border-white shadow-pawn
                            ${isMoving ? "animate-pawn-move" : ""}
                            transition-all duration-300`}
                          style={{
                            backgroundColor: player.color,
                          }}
                        />
                      ))}
                    </div>
                  )}
                </>
              )}
            </div>
          );
        })}

        {/* Center dice button */}
        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 z-20">
          {diceButton}
        </div>
      </div>
    </div>
  );
};

export default Board;
