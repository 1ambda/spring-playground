import eslint from '@eslint/js'
import stylisticTs from '@stylistic/eslint-plugin-ts'
import tsParser from '@typescript-eslint/parser'
import eslintPluginPrettierRecommended from 'eslint-plugin-prettier/recommended'
import simpleImportSort from 'eslint-plugin-simple-import-sort'
import tseslint from 'typescript-eslint'

import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import globals from 'globals'

import pluginQuery from '@tanstack/eslint-plugin-query'
import pluginRouter from '@tanstack/eslint-plugin-router'

export default [
  {
    ignores: [
      '.idea/.eslintrc.cjs',
      'dist',
      '**/node_modules/*',
      '**/test-results/*',
      '**/playwright-report/*',
    ],
    files: ['**/*.js', '**/*.ts'],
    plugins: {
      '@stylistic/ts': stylisticTs,
      'simple-import-sort': simpleImportSort,

      'react-hooks': reactHooks,
      'react-refresh': reactRefresh,

      '@tanstack/query': pluginQuery,
      '@tanstack/router': pluginRouter,
    },
    languageOptions: {
      parser: tsParser,
      ecmaVersion: 'latest',
      sourceType: 'script',
      parserOptions: {
        project: './tsconfig.json',
      },
      globals: globals.browser,
    },
    rules: {
      '@stylistic/ts/comma-dangle': ['error', 'always-multiline'],
      '@typescript-eslint/no-floating-promises': ['error'],
      'simple-import-sort/imports': 'error',
      'simple-import-sort/exports': 'error',
      '@stylistic/ts/indent': ['error', 2],

      ...reactHooks.configs.recommended.rules,
      'react-refresh/only-export-components': [
        'warn',
        { allowConstantExport: true },
      ],
      'react/react-in-jsx-scope': 'off',
      'react/prop-types': 'off',

      '@typescript-eslint/no-empty-interface': 'off',
      '@typescript-eslint/no-misused-promises': [
        2,
        {
          checksVoidReturn: { attributes: false },
        },
      ],
    },
  },
  eslint.configs.recommended,
  ...tseslint.configs.recommended,
  eslintPluginPrettierRecommended,
]
