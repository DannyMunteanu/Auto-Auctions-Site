/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {
      spacing: {
        "40vh": "40vh",
        "40vw": "40vw",
      },
    },
  },
  plugins: [
    function ({ addUtilities }) {
      addUtilities(
        {
          ".w-40vw": {
            width: "40vw",
          },
          ".h-40vh": {
            height: "40vh",
          },
        },
        ["responsive", "hover"]
      );
    },
  ],
};
