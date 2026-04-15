// Placeholder — in a real project this file is compiled by Shadow-CLJS
// from your ClojureScript/re-frame source code.
//
// Shadow-CLJS compiles your ClojureScript into this file.
// You never edit this manually.

document.getElementById("app").innerHTML = `
  <h1>Full Stack App</h1>
  <p>This placeholder will be replaced by your compiled re-frame app.</p>
  <p>Fetching feedback from the API...</p>
`;

fetch("/api/feedback")
  .then(r => r.json())
  .then(data => {
    document.getElementById("app").innerHTML += `
      <pre>${JSON.stringify(data, null, 2)}</pre>
    `;
  });
