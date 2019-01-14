function getData() {
    fetch('http://localhost:8080/api/games')
        .then(response => response.json())
        .then(response => {
            const games = response.games
            console.log(games)

        })
}
window.onload = () => getData()
