import Taro from '@tarojs/taro'
import { useState } from 'react'
import { View, Text, Input, Button, Switch } from '@tarojs/components'
import './index.scss'

export default function AddAddressPage() {
  const [name, setName] = useState('')
  const [phone, setPhone] = useState('')
  const [province, setProvince] = useState('')
  const [city, setCity] = useState('')
  const [district, setDistrict] = useState('')
  const [detail, setDetail] = useState('')
  const [isDefault, setIsDefault] = useState(false)

  const id = Taro.getCurrentInstance().router?.params.id
  const isEdit = !!id

  const handleSave = () => {
    if (!name || !phone || !detail) {
      Taro.showToast({ title: '请填写完整信息', icon: 'none' })
      return
    }
    if (!/^1\d{10}$/.test(phone)) {
      Taro.showToast({ title: '手机号格式错误', icon: 'none' })
      return
    }
    
    Taro.showToast({ title: isEdit ? '修改成功' : '添加成功', icon: 'success' })
    setTimeout(() => Taro.navigateBack(), 1000)
  }

  return (
    <View className='add-address-page'>
      <View className='form-section'>
        <View className='form-item'>
          <Text className='label'>收货人</Text>
          <Input 
            className='input'
            placeholder='请输入姓名'
            value={name}
            onInput={(e) => setName(e.detail.value)}
          />
        </View>
        <View className='form-item'>
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
      </View>

      <View className='form-section'>
        <View className='form-item'>
          <Text className='label'>所在地区</Text>
          <View className='area-inputs'>
            <Input 
              className='area-input'
              placeholder='省'
              value={province}
              onInput={(e) => setProvince(e.detail.value)}
            />
            <Input 
              className='area-input'
              placeholder='市'
              value={city}
              onInput={(e) => setCity(e.detail.value)}
            />
            <Input 
              className='area-input'
              placeholder='区/县'
              value={district}
              onInput={(e) => setDistrict(e.detail.value)}
            />
          </View>
        </View>
        <View className='form-item'>
          <Text className='label'>详细地址</Text>
          <Input 
            className='input'
            placeholder='请输入详细地址'
            value={detail}
            onInput={(e) => setDetail(e.detail.value)}
          />
        </View>
      </View>

      <View className='form-section'>
        <View className='form-item'>
          <Text className='label'>设为默认地址</Text>
          <Switch 
            checked={isDefault}
            onChange={(e) => setIsDefault(e.detail.value)}
            color='#ff4d4f'
          />
        </View>
      </View>

      <Button className='save-btn' onClick={handleSave}>
        保存地址
      </Button>
    </View>
  )
}
