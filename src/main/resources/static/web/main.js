getData();

function getData() {
    var app = new Vue({
        el: '#app',
        data: {
            slvGames: [],
            playersScore: [],
            top10score: [],
            gamesData: [],
            loginOrSignUp: true,
            errorMessage: [],
            errorStatus: false,
            res: [],
            userIslogged: false,
            leaderboardOrGames: true,
            loginOrBody: true
        },
        beforeCreate() {
                fetch('http://localhost:8080/api/games', {

                    })
                    .then(response => response.json())
                    .then(response => {
                        this.slvGames = response.games
                        console.log(this.slvGames)
//                        main(slvGames)

                    })
            fetch(`../api/games`)
                .then(response => response.json())
                .then(json => {
                    this.gamesData = json;
                    console.log(this.gamesData)
                    this.gamesData.games.sort((fst, snd) => snd.players.length - fst.players.length);
                    if (this.gamesData.current != null) {
                        this.userIslogged = true;
                    } else {
                        this.userIslogged = false;
                    }
//                    this.getBlueBacground();
                });
        },
        methods: {
//            getScore(playersList) {
//                playersList.forEach(player => {
//                    let playerInfo = {};
//                    let totalScore = 0;
//                    let wins = 0;
//                    let loses = 0;
//                    let ties = 0;
//                    player.score.forEach(score => {
//                        if (score.gameScore.playersScore == 1) {
//                            wins++;
//                        } else if (score.gameScore.playersScore == 0.5) {
//                            ties++;
//                        } else {
//                            loses++;
//                        }
//                    })
//                    totalScore = wins + (ties / 2);
//                    playerInfo = {
//                        "fullName": player.name,
//                        "totalScore": totalScore,
//                        "wins": wins,
//                        "loses": loses,
//                        "ties": ties
//                    };
//                    this.playersScore.push(playerInfo);
//                    this.playersScore.sort((fst, snd) => snd.totalScore - fst.totalScore);
//                })
//                this.playersScore.forEach(player => {
//                    if (this.top25score.length <= 25) {
//                        this.top25score.push(player);
//                    } else if (player.totalScore == this.top25score[this.top25score.length - 1].totalScore) {
//                        this.top25score.push(player);
//                    }
//                })
//            },
            login() {
//                let email = document.getElementById("email").value.toLowerCase();
//                let password = document.getElementById("password").value;
//
//                $.post("/api/login", {
//                        email: email,
//                        password: password
//                    })
//                    .then(response => {
//                        // console.log("logged in"),
//                        console.log(JSON.stringify(response)),
//                            location.reload();
//                    })
//                    .catch(error => console.error('Error:', error))
            },
            logout() {
//                $.post("/api/logout").done(function () {
//                    console.log("logged out");
//                });
//                location.reload();
            },
            loginChange() {
//                if (this.loginOrSignUp == true) {
//                    this.loginOrSignUp = false;
//                } else {
//                    this.loginOrSignUp = true;
//                }
            },
            register() {
//                let email = document.getElementById("email").value.toLowerCase();
//                let userName = document.getElementById("userName").value.toLowerCase();
//                let password = document.getElementById("password").value;
//
//                $.post("/api/players", {
//                        userName: userName,
//                        email: email,
//                        password: password
//                    })
//                    .done(res => {
//                        this.login(), console.log(res)
//                    })
//                    .fail(err => {
//                        this.errorMessage = err, console.log(this.errorMessage), this.errorStatus = true
//                    })
            },
            getLogin() {
//                this.loginOrBody = false;
            }
        }
    })
};