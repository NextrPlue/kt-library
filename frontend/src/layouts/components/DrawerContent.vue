<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '포인트' }" />
        <VerticalNavLink
            :item="{
                title: '포인트',
                to: '/points',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '작가관리' }" />
        <VerticalNavLink
            :item="{
                title: '작가',
                to: '/authors',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '고객 관리' }" />
        <VerticalNavLink
            :item="{
                title: '고객',
                to: '/customers',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독',
                to: '/subsciptions',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '집필관리' }" />
        <VerticalNavLink
            :item="{
                title: '원고',
                to: '/manuscripts',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '작가정보가져오기',
                to: '/readAuthors',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'ai 서비스' }" />
        <VerticalNavLink
            :item="{
                title: '도서',
                to: '/books',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '플랫폼' }" />
        <VerticalNavLink
            :item="{
                title: '책장',
                to: '/bookShelves',
            }"
        />
    </ul>
</template>
