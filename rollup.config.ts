import resolve from '@rollup/plugin-node-resolve';
import commonjs from '@rollup/plugin-commonjs';
import typescript from '@rollup/plugin-typescript';
import html from '@rollup/plugin-html';
// @ts-ignore
import serve from 'rollup-plugin-serve';
import { terser } from 'rollup-plugin-terser';

const extensions = [ '.ts'];
const isDev = process.env.NODE_ENV === 'development';

export default {
    input: 'src/index.ts',
    output: [
        {
            file: './dist/index.js',
            format: 'es',
            name: 'workingConfig',
            sourcemap: isDev,
        },
    ],
    plugins: [
        resolve({ extensions }),
        commonjs({
            include: '**/node_modules/**',
        }),
        typescript(),
        isDev && serve({
            contentBase: 'dist',
            port: 9001,
        }),
        html({
            title: 'JosipLogin'
        }),
        !isDev && terser(),
    ],
};
