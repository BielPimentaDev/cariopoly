import { Button } from "@/components/ui/button";
import { Dices } from "lucide-react";

interface DiceButtonProps {
  onRoll: () => void;
  disabled: boolean;
  lastRoll: number | null;
  isRolling: boolean;
}

const DiceButton = ({ onRoll, disabled, lastRoll, isRolling }: DiceButtonProps) => {
  return (
    <div className="flex flex-col items-center gap-4 bg-white/95 backdrop-blur-sm rounded-2xl p-6 shadow-xl">
      <Button
        onClick={onRoll}
        disabled={disabled}
        size="lg"
        className="px-8 py-6 text-lg font-bold rounded-xl shadow-lg
          bg-gradient-ocean hover:opacity-90 text-white
          disabled:opacity-50 disabled:cursor-not-allowed
          transition-all duration-300"
      >
        <Dices className="mr-2 h-6 w-6" />
        Jogar Dados
      </Button>
      
      {lastRoll !== null && (
        <div className="flex flex-col items-center gap-2">
          <div
            className={`w-20 h-20 bg-white rounded-xl shadow-lg flex items-center 
              justify-center border-4 border-primary
              ${isRolling ? "animate-spin" : ""}`}
          >
            <span className="text-4xl font-bold text-primary">{lastRoll}</span>
          </div>
          <p className="text-sm text-muted-foreground font-medium">
            Último resultado
          </p>
        </div>
      )}
    </div>
  );
};

export default DiceButton;
