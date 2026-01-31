import Taro from '@tarojs/taro'
import { useState } from 'react'
import { View, Text, Input, Button, Radio } from '@tarojs/components'
import './index.scss'
import { Address } from '../../../../shared/types'

export default function AddressPage() {
  const [addresses, setAddresses] = useState<Address[]>([
    {
      id: 1,
      name: '张三',
      phone: '13800138000',
      province: '北京市',
      city: '朝阳区',
      district: 'xxx街道',
      detail: 'xxx小区1号楼101',
      isDefault: true
    },
    {
      id: 2,
      name: '李四',
      phone: '13900139000',
      province: '北京市',
      city: '海淀区',
      district: 'xxx街道',
      detail: 'xxx大厦502',
      isDefault: false
    }
  ])

  const setDefault = (id: number) => {
    setAddresses(addrs => addrs.map(a => ({
      ...a,
      isDefault: a.id === id
    })))
  }

  const deleteAddress = (id: number) => {
    Taro.showModal({
      title: '删除地址',
      content: '确定要删除此地址吗？',
      success: (res) => {
        if (res.confirm) {
          setAddresses(addrs => addrs.filter(a => a.id !== id))
          Taro.showToast({ title: '已删除', icon: 'success' })
        }
      }
    })
  }

  const goToAdd = () => {
    Taro.navigateTo({ url: '/pages/address/add' })
  }

  const goToEdit = (id: number) => {
    Taro.navigateTo({ url: `/pages/address/add?id=${id}` })
  }

  return (
    <View className='address-page'>
      {addresses.map(addr => (
        <View key={addr.id} className='address-card'>
          <View className='addr-info' onClick={() => goToEdit(addr.id)}>
            <View className='user-row'>
              <Text className='name'>{addr.name}</Text>
              <Text className='phone'>{addr.phone}</Text>
              {addr.isDefault && <Text className='tag'>默认</Text>}
            </View>
            <Text className='addr-detail'>
              {addr.province}{addr.city}{addr.district}{addr.detail}
            </Text>
          </View>
          <View className='addr-actions'>
            <View className='action-item' onClick={() => setDefault(addr.id)}>
              <Radio 
                checked={addr.isDefault} 
                color='#ff4d4f'
              />
              <Text className='action-text'>设为默认</Text>
            </View>
            <View className='action-item' onClick={() => deleteAddress(addr.id)}>
              <Text className='action-text delete'>删除</Text>
            </View>
          </View>
        </View>
      ))}

      {addresses.length === 0 && (
        <View className='empty-tip'>
          <Text>暂无收货地址</Text>
        </View>
      )}

      <View className='add-btn' onClick={goToAdd}>
        + 添加新地址
      </View>
    </View>
  )
}
