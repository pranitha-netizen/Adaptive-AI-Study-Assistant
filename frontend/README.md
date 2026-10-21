# BloomLearn frontend

Open `index.html` with VS Code Live Server after the backend is running on `http://localhost:8080`.
The frontend uses plain HTML, CSS and JavaScript (no npm/build step). Bootstrap is loaded from its public CDN, so an internet connection is needed for Bootstrap styling. The app itself does not use a paid service.

The API base URL defaults to `http://localhost:8080/api`. To override it in the browser console, run:
`localStorage.setItem('bloom_api_url','http://localhost:8080/api')`
then refresh. Remove the setting to return to the default.
