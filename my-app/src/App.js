import React, { Component } from 'react';
import './App.css';
import {Map} from './Map.js';
import TextBar from './TextBar'

class App extends Component {


    render() {
      return (
          <div>
              <div style={{align: "center"}}>
                  <TextBar/>
              </div>
              <div>
                 <Map/>
              </div>
          </div>
      )
  }
}

export default App;