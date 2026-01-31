import React, { Component } from 'react'
import { Provider } from 'react-redux'
import { store } from './store'
import './styles/index.scss'

class App extends Component {
  componentDidMount () {}

  componentDidShow () {}

  componentDidHide () {}

  render () {
    return (
      <Provider store={store}>
        {this.props.children}
      </Provider>
    )
  }
}

export default App
