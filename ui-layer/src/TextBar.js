import React, { Component } from 'react';
import * as ReactDOM from "react-dom";

//     search(e) {
//         e.preventDefault();
//         let x = this.input.value;
//         console.log(x.toString());
//
//         // axios.get(`https://jsonplaceholder.typicode.com/users`)
//         //     .then(res => {
//         //         const persons = res.data;
//         //         this.setState({ persons });
//         //     })
//     }

export class TextBar extends React.Component {

    handleSubmit = (e) => {
        if(e) e.preventDefault();
        const name = this.input.value;
        const XHR = new XMLHttpRequest();
        const FD  = new FormData();


        FD.append("query", name);

        // Define what happens on successful data submission
        XHR.addEventListener( 'load', function(e) {
            alert( 'Yeah! Data sent and response loaded.' );
        } );

        // Define what happens in case of error
        XHR.addEventListener(' error', function(e) {
            alert( 'Oops! Something went wrong.' );
        } );

        // Set up our request
        XHR.open( 'POST', 'https://example.com/cors.php' );

        // Send our FormData object; HTTP headers are set automatically
        XHR.send( FD );
        console.log('Your name is', name);
    };

    render(){
        return (
            <div style={{display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '50px'}}>
            <form noValidate autoComplete="off" onSubmit={this.handleSubmit}>
                <input style={{height: '30px',
                               width: '400px' ,
                               backgroundColor: 'transparent',
                               border: '0px solid',
                               textAlign: 'center'}} placeholder="Your query" type="text" ref={(element) => { this.input = element}} />
            </form>
            </div>
        )
    }
}

ReactDOM.render(<TextBar />, document.getElementById('root'));