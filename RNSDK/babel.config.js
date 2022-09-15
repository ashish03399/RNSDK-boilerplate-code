// eslint-disable-next-line no-undef
module.exports = {
    presets: ['module:metro-react-native-babel-preset'],
    plugins: [
    // ... some other plugins
        [
            'module-resolver',
            {
                extensions: [
                    '.ios.js',
                    '.android.js',
                    '.js',
                    '.jsx',
                    '.json',
                    '.tsx',
                    '.ts',
                    '.native.js'
                ]
            }
        ],
        [
            'transform-react-remove-prop-types',
            {
                mode: 'remove',
                ignoreFilenames: ['node_modules']
            }
        ]
    ]
};
