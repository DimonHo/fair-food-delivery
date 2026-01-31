import Taro from '@tarojs/taro'
import { useState } from 'react'
import { View, Text, Input, Button } from '@tarojs/components'
import './index.scss'
import { useAppStore } from '../../store'

export default function RiderLoginPage() {
  const [phone, setPhone] = useState('')
  const [password, setPassword] = useState('')
  const { setUserInfo, setToken } = useAppStore()

  const handleLogin = () => {
    if (!phone || !password) {
      Taro.showToast({ title: '请输入账号和密码', icon: 'none' })
      return
    }
    
    // 模拟登录
    const mockUser = {
      id: 1001,
      phone,
      nickname: '骑手' + phone.slice(-4),
      avatar: ''
    }
    setUserInfo(mockUser)
    setToken('rider_token_' + Date.now())
    
    Taro.showToast({ title: '登录成功', icon: 'success' })
    setTimeout(() => {
      Taro.switchTab({ url: '/pages/home/index' })
    }, 1000)
  }

  return (
    <View className='rider-login-page'>
      <View className='login-header'>
        <Text className='logo'>🚴</Text>
        <Text className='title'>骑手端</Text>
        <Text className='subtitle'>FairFood 配送平台</Text>
      </View>

      <View className='login-form'>
        <View className='input-group'>
          <Text className='label'>手机号</Text>
          <Input 
            className='input'
            type='number'
            placeholder='请输入手机号'
            value={phone}
            onInput={(e) => setPhone(e.detail.value)}
          />
        </View>
        <View className='input-group'>
          <Text className='label'>密码</Text>
          <Input 
            className='input'
            password
            placeholder='请输入密码'
            value={password}
            onInput={(e) => setPassword(e.detail.value)}
          />
        </View>

        <Button className='login-btn' onClick={handleLogin}>
          登录
        </Button>
      </View>
    </View>
  )
}
