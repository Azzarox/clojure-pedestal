# full-stack-reframe

Pedestal backend + re-frame frontend example.

## Requirements

- [Leiningen](https://leiningen.org/)
- [Node.js / npm](https://nodejs.org/)

## Running

Open two terminals from the project root.

**Terminal 1 — Backend:**
```bash
lein run
```

**Terminal 2 — Frontend:**
```bash
npm install       # only needed once
npx shadow-cljs watch app
```

Then open http://localhost:8080.

## Project structure

```
full-stack-reframe/
├── backend/
│   └── src/backend/        # Clojure source (Pedestal server)
├── frontend/
│   └── src/frontend/       # ClojureScript source (re-frame UI)
├── resources/
│   └── public/             # Static files served by Pedestal
│       └── js/             # Compiled JS output (shadow-cljs)
├── project.clj             # Leiningen config (backend)
└── shadow-cljs.edn         # shadow-cljs config (frontend)
```
