import { useState } from "react";
import Board from "@/components/Board";
import PlayerInfo from "@/components/PlayerInfo";
import DiceButton from "@/components/DiceButton";
import GameStatus from "@/components/GameStatus";
import { toast } from "sonner";

interface Player {
  id: number;
  name: string;
  balance: number;
  color: string;
  position: number;
}

const Index = () => {
  const [players, setPlayers] = useState<Player[]>([
    { id: 1, name: "Jogador 1", balance: 1500, color: "hsl(210, 100%, 50%)", position: 0 },
    { id: 2, name: "Jogador 2", balance: 1500, color: "hsl(0, 85%, 55%)", position: 0 },
    { id: 3, name: "Jogador 3", balance: 1500, color: "hsl(140, 65%, 45%)", position: 0 },
    { id: 4, name: "Jogador 4", balance: 1500, color: "hsl(270, 70%, 55%)", position: 0 },
  ]);

  const [currentPlayerIndex, setCurrentPlayerIndex] = useState(0);
  const [lastRoll, setLastRoll] = useState<number | null>(null);
  const [isRolling, setIsRolling] = useState(false);
  const [isMoving, setIsMoving] = useState(false);
  const [gameMessage, setGameMessage] = useState("Clique em 'Jogar Dados' para começar!");

  const currentPlayer = players[currentPlayerIndex];
  const TOTAL_CELLS = 30;

  const rollDice = () => {
    if (isRolling || isMoving) return;

    setIsRolling(true);
    setGameMessage("Rolando dados...");

    // Simulate dice roll animation
    setTimeout(() => {
      const roll = Math.floor(Math.random() * 6) + 1;
      setLastRoll(roll);
      setIsRolling(false);
      
      toast.success(`${currentPlayer.name} tirou ${roll}!`, {
        description: "Movendo peão...",
      });

      movePawn(roll);
    }, 600);
  };

  const movePawn = (spaces: number) => {
    setIsMoving(true);
    setGameMessage(`Movendo ${spaces} casas...`);

    const currentPos = currentPlayer.position;
    const newPos = (currentPos + spaces) % TOTAL_CELLS;

    // Simulate gradual movement with animation
    let step = 0;
    const moveInterval = setInterval(() => {
      step++;
      const intermediatePos = (currentPos + step) % TOTAL_CELLS;
      
      setPlayers((prev) =>
        prev.map((p) =>
          p.id === currentPlayer.id ? { ...p, position: intermediatePos } : p
        )
      );

      if (step === spaces) {
        clearInterval(moveInterval);
        finishMove(newPos);
      }
    }, 300);
  };

  const finishMove = (newPosition: number) => {
    setIsMoving(false);
    
    // Check if passed start (position 0)
    const passedStart = newPosition < currentPlayer.position;
    if (passedStart) {
      setPlayers((prev) =>
        prev.map((p) =>
          p.id === currentPlayer.id ? { ...p, balance: p.balance + 200 } : p
        )
      );
      toast.success("Passou pelo Início! +R$ 200", {
        description: `${currentPlayer.name} recebeu bônus!`,
      });
    }

    // Move to next player
    const nextPlayerIndex = (currentPlayerIndex + 1) % players.length;
    setCurrentPlayerIndex(nextPlayerIndex);
    
    const nextPlayer = players[nextPlayerIndex];
    setGameMessage(`Vez de ${nextPlayer.name}!`);
    
    toast.info(`Próximo: ${nextPlayer.name}`, {
      description: "Clique em 'Jogar Dados'",
    });
  };

  return (
    <div className="min-h-screen bg-background py-8 px-4">
      <div className="container mx-auto space-y-8">
        {/* Header */}
        <div className="text-center">
          <h1 className="text-5xl font-bold bg-gradient-ocean bg-clip-text text-transparent mb-2">
            Cariopoly
          </h1>
          <p className="text-muted-foreground">Versão simplificada do jogo clássico</p>
        </div>

        {/* Player Info */}
        <PlayerInfo players={players} currentPlayer={currentPlayer.id} />

        {/* Game Status */}
        <GameStatus
          currentPlayerName={currentPlayer.name}
          currentPlayerColor={currentPlayer.color}
          message={gameMessage}
        />

        {/* Board */}
        <Board
          players={players.map((p) => ({
            id: p.id,
            position: p.position,
            color: p.color,
          }))}
          isMoving={isMoving}
          diceButton={
            <DiceButton
              onRoll={rollDice}
              disabled={isRolling || isMoving}
              lastRoll={lastRoll}
              isRolling={isRolling}
            />
          }
        />
      </div>
    </div>
  );
};

export default Index;
