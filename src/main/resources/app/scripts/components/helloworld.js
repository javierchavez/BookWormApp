import React from 'react';
var $ = require ('jquery');

var HelloWorld = React.createClass ({
    getInitialState: function () {
        return {
            showLogin: true,
            showMainView: false,
            user : '',
            password: ''
        }
    },
    loginSuccessful: function () {
        this.setState({showLogin:true,showMainView:false})
    },
    render: function() {
        /*$.get("/hello", function (result) {
            console.log(result);
        });*/

        return (
            <div>
                {this.state.showLogin ? <Login loginSuccessful={this.loginSuccessful}/> : null}
                {this.state.showMainView ? <MainView /> :null}
            </div>
        )
    }
})

/*
var testComponent = React.creatClass({
})
*/


var Login = React.createClass ({
    /*
    constructor() {
        this.state = {
            user: '',
            password: ''
        };
    },

    login(e) {
        e.preventDefault();
        // Here, we call an external AuthService. We’ll create it in the next step
        Auth.login(this.state.user, this.state.password)
            .catch(function (err) {
                console.log("Error logging in",err);
            });
    },
    */
    getInitialState: function () {
        return {
            showError: false
        }
    },

    loginCheck: function () {
        $.get("/login", function (result) {
            if (result == true) {
                this.props.loginSuccessful
            }
            else {
                this.setState({showError: true})
            }
        })
    },

    render: function() {

        console.log("Insider Function")
        return(
            /*
            <div>
                <h1>Login Page</h1>
                <p>{this.props.id}</p>

            </div>
            */
           <div>
               <center>
                   <h2>Login You Idiot!</h2>
                   <form role="form" method="post">
                       <br/>user:<input type="text" name="username"/>
                       <br/>password:<input type="password" name="password"/>
                       {this.state.showError ? <h2> Failed to Authenticate</h2> : null}
                   </form>
               </center>
           </div>
        )
    },

    onSubmit: function() {
        alert('Submit Button selected!')
    },

    componentDidMount: function () {
        setTimeout(this.props.loginSuccessful(),3000);
        console.log('inhere')
    }
})

var MainView = React.createClass ({
    render: function() {
        /*api get
        success: data = myresult*/
        return(
            <div>
                <h1>Main View Page</h1>
            </div>
        )
    }
})
/*
var LoginProcessor =function(){
    proces the request via API
    if valid, run loginSuccessful

})*/
module.exports = HelloWorld;

    /*
var TestComponent = React.createClass({
    render: function() {
        return (
            <h2>My name is Dom</h2>
        );
    }
}); */

/*(<TestComponent />, document.getElementById('content')); */