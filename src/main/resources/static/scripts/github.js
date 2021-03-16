fetch('partial/github')
    .then((response) => response.text())
    .then(html => {
        new Vue({
            el: '#app',
            data: () => {
                return {
                    user: null,
                    username: null,
                    loading: false,
                    darkTheme: true
                }
            },
            methods: {
                getUser: function () {
                    this.loading = true;
                    fetch(`api/v1/github/${this.username}`)
                        .then((response) => response.json())
                        .then(userJson => {
                            this.user = userJson;
                            this.loading = false;
                        });
                },
                setAndGetUser(username) {
                    this.username = username;
                    this.getUser();
                },
                switchTheme($event) {
                    this.darkTheme = $event.currentTarget.value == "true";
                    document.body.dataset.theme = this.darkTheme ? "darkly" : "flatly";
                    document.querySelector('#themeLink').href = `https://bootswatch.com/4/${document.body.dataset.theme}/bootstrap.min.css`
                },
                getReadme($event, repo) {
                    $event.currentTarget.remove();
                    this.loading = true;
                    fetch(`/api/v1/github/${repo.full_name}/readme`)
                        .then((response) => response.text())
                        .then(text => {
                            const converter = new showdown.Converter();
                            repo.readme = converter.makeHtml(text);
                            this.loading = false;
                        });
                }
            },
            template: html
        })
    });