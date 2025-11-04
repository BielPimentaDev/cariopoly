import type { Config } from "tailwindcss";

export default {
  darkMode: ["class"],
  content: ["./pages/**/*.{ts,tsx}", "./components/**/*.{ts,tsx}", "./app/**/*.{ts,tsx}", "./src/**/*.{ts,tsx}"],
  prefix: "",
  theme: {
    container: {
      center: true,
      padding: "2rem",
      screens: {
        "2xl": "1400px",
      },
    },
    extend: {
      colors: {
        border: "hsl(var(--border))",
        input: "hsl(var(--input))",
        ring: "hsl(var(--ring))",
        background: "hsl(var(--background))",
        foreground: "hsl(var(--foreground))",
        primary: {
          DEFAULT: "hsl(var(--primary))",
          foreground: "hsl(var(--primary-foreground))",
        },
        secondary: {
          DEFAULT: "hsl(var(--secondary))",
          foreground: "hsl(var(--secondary-foreground))",
        },
        destructive: {
          DEFAULT: "hsl(var(--destructive))",
          foreground: "hsl(var(--destructive-foreground))",
        },
        muted: {
          DEFAULT: "hsl(var(--muted))",
          foreground: "hsl(var(--muted-foreground))",
        },
        accent: {
          DEFAULT: "hsl(var(--accent))",
          foreground: "hsl(var(--accent-foreground))",
        },
        popover: {
          DEFAULT: "hsl(var(--popover))",
          foreground: "hsl(var(--popover-foreground))",
        },
        card: {
          DEFAULT: "hsl(var(--card))",
          foreground: "hsl(var(--card-foreground))",
        },
        player: {
          1: "hsl(var(--player-1))",
          2: "hsl(var(--player-2))",
          3: "hsl(var(--player-3))",
          4: "hsl(var(--player-4))",
        },
        board: {
          bg: "hsl(var(--board-bg))",
          cell: "hsl(var(--cell-bg))",
          border: "hsl(var(--cell-border))",
        },
      },
      backgroundImage: {
        'gradient-ocean': 'var(--gradient-ocean)',
        'gradient-tropical': 'var(--gradient-tropical)',
        'gradient-sunset': 'var(--gradient-sunset)',
      },
      boxShadow: {
        'board': 'var(--shadow-board)',
        'card-custom': 'var(--shadow-card)',
        'pawn': 'var(--shadow-pawn)',
      },
      borderRadius: {
        lg: "var(--radius)",
        md: "calc(var(--radius) - 2px)",
        sm: "calc(var(--radius) - 4px)",
      },
      keyframes: {
        "accordion-down": {
          from: { height: "0" },
          to: { height: "var(--radix-accordion-content-height)" },
        },
        "accordion-up": {
          from: { height: "var(--radix-accordion-content-height)" },
          to: { height: "0" },
        },
        "pawn-move": {
          "0%": { transform: "scale(1)" },
          "50%": { transform: "scale(1.2) translateY(-8px)" },
          "100%": { transform: "scale(1)" },
        },
        "dice-roll": {
          "0%, 100%": { transform: "rotate(0deg)" },
          "25%": { transform: "rotate(90deg)" },
          "50%": { transform: "rotate(180deg)" },
          "75%": { transform: "rotate(270deg)" },
        },
        "pulse-glow": {
          "0%, 100%": { boxShadow: "0 0 10px hsl(var(--primary) / 0.5)" },
          "50%": { boxShadow: "0 0 20px hsl(var(--primary) / 0.8)" },
        },
      },
      animation: {
        "accordion-down": "accordion-down 0.2s ease-out",
        "accordion-up": "accordion-up 0.2s ease-out",
        "pawn-move": "pawn-move 0.5s cubic-bezier(0.68, -0.55, 0.265, 1.55)",
        "dice-roll": "dice-roll 0.6s ease-in-out",
        "pulse-glow": "pulse-glow 2s ease-in-out infinite",
      },
    },
  },
  plugins: [require("tailwindcss-animate")],
} satisfies Config;
