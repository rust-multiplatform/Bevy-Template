use bevy::prelude::*;

pub struct GreetPeoplePlugin;

#[derive(Component)]
struct Person;

#[derive(Component)]
struct Name(String);

impl Plugin for GreetPeoplePlugin {
    fn build(&self, app: &mut App) {
        app
            // Startup Systems run once at startup
            .add_startup_system(add_persons)
            // Normal Systems run every frame
            .add_system(greet_people);
    }
}

fn add_persons(mut commands: Commands) {
    commands.spawn((Person, Name("from Rust".to_string())));
    commands.spawn((Person, Name("Someone".to_string())));
    commands.spawn((Person, Name("SomeFirstName SomeLastName".to_string())));
}

fn greet_people(query: Query<&Name, With<Person>>) {
    for person in query.iter() {
        println!("Hello {}!", person.0);
    }
}
