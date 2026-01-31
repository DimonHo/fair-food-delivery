import Taro from '@tarojs/taro'
import { useState } from 'react'
import { View, Text, Input, Button } from '@tarojs/components'
import './index.scss'
import { useAppStore } from '../../store'

export default function LoginPage() {
  const [phone, setPhone] = useState('')
  const [code, setCode] = useState('')
  const [countdown, setCountdown] = useState(0)
  const { setUserInfo, setToken } = useAppStore()

  const getCode = () => {
    if (!/^1\d{10}$/.test(phone)) {
      Taro.showToast({ title: '请输入正确手机号', icon: 'none' })
      return
    }
    setCountdown(60)
    const timer = setInterval(() => {
      setCountdown(c => {
        if (c <= 1) {
          clearInterval(timer)
          return 0
        }
        return c - 1
      })
    }, 1000)
    Taro.showToast({ title: '验证码已发送', icon: 'success' })
  }

  const handleLogin = () => {
    if (!/^1\d{10}$/.test(phone)) {
      Taro.showToast({ title: '请输入正确手机号', icon: 'none' })
      return
    }
    if (!/^\d{4,6}$/.test(code)) {
      Taro.showToast({ title: '请输入4-6位验证码', icon: 'none' })
      return
    }
    
    // 模拟登录成功
    const mockUser = {
      id: 1,
      phone: phone,
      nickname: '用户' + phone.slice(-4),
      avatar: ''
    }
    setUserInfo(mockUser)
    setToken('mock_token_' + Date.now())
    
    Taro.showToast({ title: '登录成功', icon: 'success' })
    setTimeout(() => {
      Taro.switchTab({ url: '/pages/index/index' })
    }, 1000)
  }

  return (
    <View className='login-page'>
      <View className='logo-area'>
        <Text className='logo-text'>FairFood</Text>
        <Text className='slogan'>美味即刻送达</Text>
      </View>

      <View className='form-area'>
        <View className='input-group'>
          <Text className='label'>手机号</Text>
          <Input 
            className='input'
            type='number'
            maxLength={11}
            placeholder='请输入手机号'
            value={phone}
            onInput={(e) => setPhone(e.detail.value)}
          />
        </View>

        <View className='input-group'>
          <Text className='label'>验证码</Text>
          <Input 
            className='input'
            type='number'
            maxLength={6}
            placeholder='请输入验证码'
            value={code}
            onInput={(e) => setCode(e.detail.value)}
          />
          <View 
            className={`code-btn ${countdown > 0 ? 'disabled' : ''}`}
            onClick={getCode}
          >
            {countdown > 0 ? `${countdown}s` : '获取验证码'}
          </View>
        </View>

        <Button className='login-btn' onClick={handleLogin}>
          登录
        </Button>

        <Text className='agreement'>
          登录即表示同意《用户协议》和《隐私政策》
        </Text>
      </View>
    </View>
  )
}
