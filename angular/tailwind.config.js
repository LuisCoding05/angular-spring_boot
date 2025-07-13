/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
    "./src/**/*.{js,jsx,ts,tsx}",
    "./src/**/*.component.html",
    "./src/**/*.component.ts"
  ],
  darkMode: 'class',
  theme: {
    extend: {
      backgroundImage: {
        'techflix-dark': 'linear-gradient(154deg, rgba(184, 57, 141, 1) 0%, rgba(189, 113, 179, 1) 25%, rgba(194, 105, 182, 1) 50%, rgba(142, 99, 173, 1) 75%, rgba(92, 65, 102, 1) 100%)',
      }
    },
  },
  plugins: [],

}