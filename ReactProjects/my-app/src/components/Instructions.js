import React, { Component } from 'react';
import ems from './rainbow.jpg';

export class Instructions extends Component {
  render() {
    return(
    <div>
    <h1>Employee Mern Project</h1>
    <p>This is an Employee Management System</p>
    <img src={ems} alt="Happy girl in green sweater" />;
      </div>
    )
  }
}
export default Instructions;