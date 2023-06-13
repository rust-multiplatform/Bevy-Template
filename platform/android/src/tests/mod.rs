<<<<<<< HEAD
// use crate::main;

// Example test:
// #[test]
// fn check_main_exists() {
//     main();
// }
=======
use crate::android_main;

#[test]
fn check_main_exists() {
    println!("This requires a Desktop Environment up and running! Tests will fail otherwise.");
    android_main();
}
>>>>>>> upstream/main

// Example for UI tests:
// #[test]
// #[cfg(feature = "ui-tests")]
// fn some_ui_test() {
//     assert!(true);
// }
